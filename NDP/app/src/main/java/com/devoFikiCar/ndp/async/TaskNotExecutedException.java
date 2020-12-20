/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.ndp.async;

public class TaskNotExecutedException extends Exception {
    public TaskNotExecutedException() {
        super("Error has occurred.");
    }
}
