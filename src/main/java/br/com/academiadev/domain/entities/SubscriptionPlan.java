package br.com.academiadev.domain.entities;

public interface SubscriptionPlan {
    boolean canEnroll(int activeEnrollments);
    String getTypeName();
}
