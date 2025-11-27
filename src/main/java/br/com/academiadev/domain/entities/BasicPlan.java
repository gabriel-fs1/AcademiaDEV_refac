package br.com.academiadev.domain.entities;

public class BasicPlan implements SubscriptionPlan {
    @Override
    public boolean canEnroll(int activeEnrollments) {
        return activeEnrollments < 3;
    }

    @Override
    public String getTypeName() {
        return "BASIC";
    }
}
