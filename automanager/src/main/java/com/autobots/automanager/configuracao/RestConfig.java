package com.autobots.automanager.configuracao;

import com.autobots.automanager.entidades.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.useHalAsDefaultJsonMediaType(false);
        config.getExposureConfiguration()
                .forDomainType(Empresa.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

        config.getExposureConfiguration()
                .forDomainType(Usuario.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

        config.getExposureConfiguration()
                .forDomainType(Veiculo.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

        config.getExposureConfiguration()
                .forDomainType(Mercadoria.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

        config.getExposureConfiguration()
                .forDomainType(Servico.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

        config.getExposureConfiguration()
                .forDomainType(Venda.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

        config.getExposureConfiguration()
                .forDomainType(ServicoPrestado.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));
    }
}


