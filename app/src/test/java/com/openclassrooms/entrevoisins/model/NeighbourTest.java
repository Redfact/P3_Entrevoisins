package com.openclassrooms.entrevoisins.model;


import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class NeighbourTest {
    Neighbour neighbour;

    @Before
    public void setup() {
        neighbour = new Neighbour(0,"Marco Polo", "Url", "adress","phoneNumber","About me");
    }

    @Test
    public void testIsFavorite() {
        assertEquals(false, neighbour.isFavorite());
    }

    @Test
    public void testSetIsFavorite() {
        neighbour.setIsFavorite();
        assertEquals(true, neighbour.isFavorite());
        neighbour.setIsFavorite();
        assertEquals(false, neighbour.isFavorite());
    }
}