package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DummyNeighbourApiServiceTest extends TestCase {
    List<Neighbour> neighbours;
    NeighbourApiService mApiService= DI.getNeighbourApiService();
    DummyNeighbourApiService dummyNeighbourApiService = new DummyNeighbourApiService();

    @Before
    public void setup(){
        dummyNeighbourApiService =  new DummyNeighbourApiService();
    }

    @Test
    public void testSetNeighbourToFavoriteStatus(){
        Neighbour neighbour = dummyNeighbourApiService.getNeighbours().get(0);
        dummyNeighbourApiService.setNeighbourFavoritesStatus(neighbour);

        List<Neighbour> favorites = dummyNeighbourApiService.getFavoritesNeighbours() ;
        assertEquals(favorites.get(0),neighbour);
    }

    @Test
    public void testGetFavoritesNeighbours() {
        dummyNeighbourApiService.setNeighbourFavoritesStatus(dummyNeighbourApiService.getNeighbours().get(1));
        dummyNeighbourApiService.setNeighbourFavoritesStatus(dummyNeighbourApiService.getNeighbours().get(2));
        dummyNeighbourApiService.setNeighbourFavoritesStatus(dummyNeighbourApiService.getNeighbours().get(3));

        List<Neighbour> favorites = dummyNeighbourApiService.getFavoritesNeighbours() ;
        assertEquals(favorites.size(),4);
        assertEquals(favorites.get(1) , dummyNeighbourApiService.getNeighbours().get(1) );
        assertEquals(favorites.get(2) , dummyNeighbourApiService.getNeighbours().get(2) );
        assertEquals(favorites.get(3) , dummyNeighbourApiService.getNeighbours().get(3) );
    }

}