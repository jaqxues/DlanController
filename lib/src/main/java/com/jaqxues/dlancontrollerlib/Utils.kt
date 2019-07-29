package com.jaqxues.dlancontrollerlib

import java.io.Reader

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project DlanController.<br>
 * Date: 29.07.2019 - Time 15:17.
 */
fun Reader.contains(string: String): Boolean {
    var byte = read()
    var found = 0
    while (byte != -1) {
        if (string[found] == byte.toChar()) {
            if (++found >= string.length)
                return true
        } else {
            found = 0
        }
        byte = read()
    }
    return false
}