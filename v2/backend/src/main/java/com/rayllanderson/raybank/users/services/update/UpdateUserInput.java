package com.rayllanderson.raybank.users.services.update;

public record UpdateUserInput(String id, String previousFirstName,
                              String previousLastName,
                              String updatedFirstName,
                              String updatedLastName) {
}
