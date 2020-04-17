package com.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import java.util.Iterator;
import java.util.List;

public class PersistenceUtil {
    private static EntityManagerFactory emf;

    private static void createEmf() {
        emf = null;
        PersistenceProviderResolver resolver = PersistenceProviderResolverHolder.getPersistenceProviderResolver();
        List<PersistenceProvider> providers = resolver.getPersistenceProviders();
        Iterator var5 = providers.iterator();

        while(var5.hasNext()) {
            PersistenceProvider provider = (PersistenceProvider)var5.next();
            System.out.println(provider);
            emf = provider.createEntityManagerFactory("Bag_pl_in_PU", null);
            if (emf != null) {
                break;
            }
        }
    }

    public static EntityManager getEntityManager(){
        if(emf == null) createEmf();
        return emf.createEntityManager();
    }
}
