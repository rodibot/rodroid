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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Transport transport = new Transport();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button moveLeft = (Button) findViewById(R.id.buttonLeft);
        assert moveLeft != null;
        moveLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    transport.moveLeft();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    transport.restoreState();
                }
                return false;
            }
        });

        Button moveRight = (Button) findViewById(R.id.buttonRight);
        assert moveRight != null;
        moveRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    transport.moveRight();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    transport.restoreState();
                }
                return false;
            }
        });
    }

    public void moveForward(View view) {
        transport.moveForward();
    }

    public void moveReverse(View view) {
        transport.moveReverse();
    }

    public void stop(View view) {
        transport.stop();
    }
}
