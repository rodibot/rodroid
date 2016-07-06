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

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Transport {

    private static final RetryPolicy RETRY_POLICY = new DefaultRetryPolicy(400, 0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private static final String HOSTNAME = "192.168.4.1";

    private static final String PORT = "1234";

    private enum Commands {
        BLINK(1), SENSE(2), MOVE(3), SING(4), SEE(5),
        PIXEL(6), LIGHT(7), LED(8), IMU(9);

        private int value;

        Commands(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private int speed = 0;

    private RequestQueue queue = Volley.newRequestQueue(Rodroid.getAppContext());

    public void moveForward() {
        speed = 100;
        sendCommand(Commands.MOVE, speed, speed);
    }

    public void stop() {
        speed = 0;
        sendCommand(Commands.MOVE, speed, speed);
    }

    public void moveReverse() {
        speed = -100;
        sendCommand(Commands.MOVE, speed, speed);
    }

    public void moveLeft() {
        sendCommand(Commands.MOVE, speed == 0 ? -100 : 0, speed == 0 ? 100 : speed);
    }

    public void moveRight() {
        sendCommand(Commands.MOVE, speed == 0 ? 100 : speed, speed == 0 ? -100 : 0);
    }

    public void restoreState() {
        sendCommand(Commands.MOVE, speed, speed);
    }

    private void sendCommand(Commands cmd, Integer param1, Integer param2) {
        String url = "http://" + HOSTNAME + ":" + PORT + "/" + cmd.getValue() + "/" + param1 + "/" + param2;

        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = Rodroid.getAppContext().getString(R.string.errorMessage);
                Toast.makeText(Rodroid.getAppContext(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
        req.setRetryPolicy(RETRY_POLICY);
        queue.add(req);
    }
}
