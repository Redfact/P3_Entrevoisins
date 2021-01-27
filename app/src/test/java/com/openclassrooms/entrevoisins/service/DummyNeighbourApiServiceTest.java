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
    public void testGetFavoritesNeighbours() {
        dummyNeighbourApiService.getNeighbours().get(0).setIsFavorite();
        dummyNeighbourApiService.getNeighbours().get(1).setIsFavorite();
        dummyNeighbourApiService.getNeighbours().get(2).setIsFavorite();
        List<Neighbour> favorites = dummyNeighbourApiService.getFavoritesNeighbours() ;
        assertEquals(favorites.size(),3);
        assertEquals(favorites.get(0) , dummyNeighbourApiService.getNeighbours().get(0) );
        assertEquals(favorites.get(1) , dummyNeighbourApiService.getNeighbours().get(1) );
        assertEquals(favorites.get(2) , dummyNeighbourApiService.getNeighbours().get(2) );
    }
}