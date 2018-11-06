package com.example.kernelsanders.baseapp;

public class SensorFilter
{
    private SensorFilter()
    {}

    public static double sum(double[] arr)
    {
        double ret = 0;
        for(int i = 0; i < arr.length; i++)
            ret += arr[i];
        return ret;
    }

    public static double[] cross(double[] arrA, double[] arrB)
    {
        double[] ret = new double[3];
        ret[0] = arrA[1]*arrB[2] -  arrA[2]*arrB[1];
        ret[1] = arrA[2]*arrB[0] -  arrA[0]*arrB[2];
        ret[2] = arrA[0]*arrB[1] -  arrA[1]*arrB[0];
        return ret;
    }

    public static double norm(double[] arr)
    {
        double ret = 0;
        for(int i = 0; i < arr.length; i++)
            ret += arr[i]*arr[i];
        return ret;
    }

    public static double dot(double[] a, double[] b)
    {
        return a[0]*b[0] + a[1]*b[1] + a[2]*b[2];
    }

    public static double[] normalize(double[] arr)
    {
        double[] ret = new double[arr.length];
        double norm = norm(arr);
        for(int i = 0; i < arr.length; i++)
            ret[i] = arr[i]/norm;
        return ret;
    }
}
