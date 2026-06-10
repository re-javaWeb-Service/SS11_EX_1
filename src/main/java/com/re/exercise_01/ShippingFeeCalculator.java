package com.re.exercise_01;

public class ShippingFeeCalculator {

    public double calculateFee(double weightKg, double distanceKm) {
        if (weightKg <= 0 || distanceKm <= 0) {
            throw new IllegalArgumentException("Weight and distance must be positive");
        }

        // 1. Tính phí cân nặng (Sửa Math.floor thành Math.ceil)
        double weightFee = 50000; // Giá cơ bản cho 1kg đầu tiên
        if (weightKg > 1) {
            weightFee += Math.ceil(weightKg - 1) * 10000;
        }

        // 2. Tính phí khoảng cách (Tính theo bậc lũy tiến)
        double distanceFee = 0;
        if (distanceKm >= 10 && distanceKm < 50) {
            distanceFee = (distanceKm - 10) * 5000;
        } else if (distanceKm >= 50) {
            // 40km (từ km 10 đến km 50) * 5000 = 200.000
            distanceFee = (40 * 5000) + ((distanceKm - 50) * 4000);
        }

        return weightFee + distanceFee;
    }
}
