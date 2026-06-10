package com.re.exercise_01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ShippingFeeCalculatorTest {

    private ShippingFeeCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ShippingFeeCalculator();
    }

    // Kịch bản 1: Cân nặng <= 1kg, khoảng cách < 10km.
    @Test
    void testCalculateFee_WeightUnder1Kg_DistanceUnder10Km() {
        // Cân nặng: 0.5kg -> 50,000 | Khoảng cách: 5km -> 0
        double fee = calculator.calculateFee(0.5, 5);
        assertThat(fee).isEqualTo(50000.0);
    }

    // Kịch bản 2: Cân nặng > 1kg (số nguyên), khoảng cách trong khoảng 10km đến 50km.
    @Test
    void testCalculateFee_IntegerWeightOver1Kg_DistanceBetween10And50Km() {
        // Cân nặng: 3kg -> 50,000 + 2*10,000 = 70,000
        // Khoảng cách: 20km -> (20 - 10) * 5000 = 50,000
        // Tổng: 120,000
        double fee = calculator.calculateFee(3.0, 20);
        assertThat(fee).isEqualTo(120000.0);
    }

    // Kịch bản 3: Cân nặng là số lẻ (1.5kg, 2.3kg), khoảng cách > 50km.
    @ParameterizedTest
    @CsvSource({
            "1.5, 60, 300000.0", // W: 50k + 10k(ceil 0.5)=60k | D: 200k + 10*4k=240k -> 300k
            "2.3, 55, 290000.0"  // W: 50k + 20k(ceil 1.3)=70k | D: 200k + 5*4k=220k -> 290k
    })
    void testCalculateFee_DecimalWeight_DistanceOver50Km(double weight, double distance, double expectedFee) {
        double fee = calculator.calculateFee(weight, distance);
        assertThat(fee).isEqualTo(expectedFee);
    }

    // Kịch bản 4: Khoảng cách đúng 10km và đúng 50km.
    @Test
    void testCalculateFee_DistanceExactlyBoundaries() {
        // Biên 10km: W(1kg) = 50,000 | D(10km) = 0 -> Tổng: 50,000
        assertThat(calculator.calculateFee(1.0, 10)).isEqualTo(50000.0);

        // Biên 50km: W(1kg) = 50,000 | D(50km) = 200,000 -> Tổng: 250,000
        assertThat(calculator.calculateFee(1.0, 50)).isEqualTo(250000.0);
    }

    // Kịch bản 5: Kiểm tra trường hợp đầu vào không hợp lệ (ném exception).
    @ParameterizedTest
    @CsvSource({
            "0, 10",
            "-1, 20",
            "2, 0",
            "2, -5",
            "-2, -2"
    })
    void testCalculateFee_InvalidInputs_ShouldThrowException(double weight, double distance) {
        assertThatThrownBy(() -> calculator.calculateFee(weight, distance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Weight and distance must be positive");
    }
}
