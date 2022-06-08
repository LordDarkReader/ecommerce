package com.czaki.ecommerce.config;

import com.czaki.ecommerce.entity.Country;
import com.czaki.ecommerce.entity.Order;
import com.czaki.ecommerce.entity.Product;
import com.czaki.ecommerce.entity.ProductCategory;
import com.czaki.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig  implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManger) {
        entityManager = theEntityManger;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedMethod = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        disableHttpMethods(Product.class, config, theUnsupportedMethod);
        disableHttpMethods(ProductCategory.class, config, theUnsupportedMethod);
        disableHttpMethods(Country.class, config, theUnsupportedMethod);
        disableHttpMethods(State.class, config, theUnsupportedMethod);
        disableHttpMethods(Order.class, config, theUnsupportedMethod);

        exposeIds(config);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins("http://*");
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedMethod) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethod))
                .withCollectionExposure((metdata, httpMethods) ->  httpMethods.disable(theUnsupportedMethod));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
