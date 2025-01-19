package org.fslabs.PerformanceTest;

public record PersonJSON(
        String fullName ,
        int age ,
        boolean employed ,
        double salary ,
        long bankAccountNumber ,
        int bankBalance
) {
}
