package com.aol.identity.findDups.util;

import com.github.joschi.jadconfig.Parameter;

/**
 * Created with IntelliJ IDEA.
 * User: pthairu
 * Date: 6/1/12
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigProps {
    @Parameter("bloom.elements")
    private int elements = 10000;

    @Parameter("bloom.probability")
    private double probability = 0.01;

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
