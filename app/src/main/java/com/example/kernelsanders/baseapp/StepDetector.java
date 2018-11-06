package com.example.kernelsanders.baseapp;

public class StepDetector
{
    private static final int ACCEL_RING_SIZE = 50;
    private static final int VEL_RING_SIZE = 10;
    private static final int STEP_DELAY_NS = 250000000;
    private static final double STEP_THRESHOLD = 50.0;

    private int accelRingCounter = 0;
    private double[] accelRingX = new double[ACCEL_RING_SIZE];
    private double[] accelRingY = new double[ACCEL_RING_SIZE];
    private double[] accelRingZ = new double[ACCEL_RING_SIZE];
    private int velRingCounter = 0;
    private double[] velRing = new double[VEL_RING_SIZE];
    private long lastStepTimeNs = 0;
    private double oldVelocityEstimate = 0;

    private StepListener listener;

    public void registerListener(StepListener listener)
    {
        this.listener = listener;
    }

    public void updateAccel(long tNs, double x, double y, double z)
    {
        double[] currAccel = new double[3];
        currAccel[0] = x;
        currAccel[1] = y;
        currAccel[2] = z;

        accelRingCounter++;
        accelRingX[accelRingCounter % ACCEL_RING_SIZE] = currAccel[0];
        accelRingY[accelRingCounter % ACCEL_RING_SIZE] = currAccel[1];
        accelRingZ[accelRingCounter % ACCEL_RING_SIZE] = currAccel[2];

        double[] worldZ = new double[3];
        worldZ[0] = SensorFilter.sum(accelRingX) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[1] = SensorFilter.sum(accelRingY) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[2] = SensorFilter.sum(accelRingZ) / Math.min(accelRingCounter, ACCEL_RING_SIZE);

        double normFactor = SensorFilter.norm(worldZ);

        worldZ[0] /= normFactor;
        worldZ[1] /= normFactor;
        worldZ[2] /= normFactor;

        double currZ = SensorFilter.dot(worldZ, currAccel) - normFactor;
        velRingCounter++;
        velRing[velRingCounter % VEL_RING_SIZE] = currZ;

        double velEstim = SensorFilter.sum(velRing);

        if(velEstim > STEP_THRESHOLD && oldVelocityEstimate <= STEP_THRESHOLD && (tNs - lastStepTimeNs > STEP_DELAY_NS))
        {
            listener.step(tNs);
            lastStepTimeNs = tNs;
        }
        oldVelocityEstimate = velEstim;
    }

}
