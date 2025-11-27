package br.com.academiadev.domain.entities;

public class PremiumPlan implements SubscriptionPlan {
    @Override
    public boolean canEnroll(int activeEnrollments) {
        return true;
    }

    @Override
    public String getTypeName() {
        return "PREMIUM";
    }
}
