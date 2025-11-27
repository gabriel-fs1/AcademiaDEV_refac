package br.com.academiadev.domain;

import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.PremiumPlan;
import br.com.academiadev.domain.entities.SubscriptionPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionPlanTest {

    @Test
    @DisplayName("Plano Básico deve bloquear a 4ª matrícula")
    void basicPlanShouldLimitEnrollments() {
        SubscriptionPlan plan = new BasicPlan();
        assertTrue(plan.canEnroll(0));
        assertTrue(plan.canEnroll(2));
        assertFalse(plan.canEnroll(3)); 
    }

    @Test
    @DisplayName("Plano Premium deve permitir matrículas ilimitadas")
    void premiumPlanShouldAllowUnlimitedEnrollments() {
        SubscriptionPlan plan = new PremiumPlan();
        assertTrue(plan.canEnroll(0));
        assertTrue(plan.canEnroll(3));
        assertTrue(plan.canEnroll(100));
    }
}
