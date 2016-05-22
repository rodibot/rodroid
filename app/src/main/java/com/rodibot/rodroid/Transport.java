/*
 * Copyright (C) 2016 Javier Martinez Canillas <javier@dowhile0.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rodibot.rodroid;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Transport {

    private enum COMMANDS {
        BLINK(1), SENSE(2), MOVE(3), SING(4), SEE(5),
        PIXEL(6), LIGHT(7), LED(8), IMU(9);

        private int value;

        COMMANDS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    String hostname = "192.168.4.1";
    String port = "1234";
    RequestQueue queue = Volley.newRequestQueue(Rodroid.getAppContext());

    public void moveForward() {
        sendCommand(COMMANDS.MOVE, 100, 100);
    }

    public void moveReverse() {
        sendCommand(COMMANDS.MOVE, -100,-100);
    }

    public void moveLeft() {
        sendCommand(COMMANDS.MOVE, -100, 100);
    }

    public void moveRight() {
        sendCommand(COMMANDS.MOVE, 100, -100);
    }

    public void stop() {
        sendCommand(COMMANDS.MOVE, 0, 0);
    }

    private void sendCommand(COMMANDS cmd, Integer param1, Integer param2) {
        String url = "http://" + hostname + ":" + port + "/" + cmd.getValue() + "/" + param1 + "/" + param2;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Communication with RoDI failed");
                error.printStackTrace();
            }});
        queue.add(stringRequest);
    }
}
