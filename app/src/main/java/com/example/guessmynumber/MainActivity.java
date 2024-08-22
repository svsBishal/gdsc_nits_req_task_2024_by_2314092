package com.example.guessmynumber;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView points_rem_num, attempts_left_num, result_text, hint_text, points_text, total_attempts_text, total_left_text, total_attempts_num;
    ImageView logo, three_line, rules;
    TextView[] options;
    int[] option_number;
    EditText editText;
    Button reset_btn, hints_btn, guess_btn;
    int attempts_left_count = 5, points_remaining_count = 100;
    int target_index;
    boolean isHintUsed;
    Drawable logo_white, gold_logo;
    LottieAnimationView animation;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
        points_rem_num = findViewById(R.id.points_rem_num);
        attempts_left_num = findViewById(R.id.attempts_left_num);
        result_text = findViewById(R.id.result_text);
        hint_text = findViewById(R.id.hint_text);
        points_text = findViewById(R.id.points_text);
        total_attempts_text = findViewById(R.id.total_attempts_text);
        total_left_text = findViewById(R.id.total_left_text);
        total_attempts_num = findViewById(R.id.total_attempts_num);
        rules = findViewById(R.id.rules);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_draw);

        animation = findViewById(R.id.l_anim);

        option_number = new int[6];

        options = new TextView[6];
        options[0] = findViewById(R.id.option_num_1);
        options[1] = findViewById(R.id.option_num_2);
        options[2] = findViewById(R.id.option_num_3);
        options[3] = findViewById(R.id.option_num_4);
        options[4] = findViewById(R.id.option_num_5);
        options[5] = findViewById(R.id.option_num_6);

        editText = findViewById(R.id.editText);

        reset_btn = findViewById(R.id.reset_btn);
        hints_btn = findViewById(R.id.hints_btn);
        guess_btn = findViewById(R.id.guess_btn);

        startNewGame();

        guess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgement();
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });

        hints_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attempts_left_count == 0) {
                    hint_text.setText("Game is over\nStart a new game");
                }
                if(isHintUsed) {
                    Toast.makeText(MainActivity.this, "You can't use hint option twice", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    AlertDialog.Builder hintAlert = new AlertDialog.Builder(MainActivity.this);
                    hintAlert.setTitle("Do you want hint?");
                    hintAlert.setMessage("You can use hint only once.\nIf you use hint option 30 points will be deducted\nAre you sure to proceed?");

                    hintAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            points_remaining_count = points_remaining_count - 30;
                            points_rem_num.setText(String.valueOf(points_remaining_count));
                            points_text.setText("points remaining");
                            makeHints();
                            isHintUsed = true;
                        }

                    });

                    hintAlert.setNegativeButton("No", null);
                    hintAlert.show();
                }
            }
        });

        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder rulesBox = new AlertDialog.Builder(MainActivity.this);
                rulesBox.setTitle("Rules");
                rulesBox.setMessage(R.string.rules);
                rulesBox.setPositiveButton("Got it", null);
                rulesBox.show();
            }
        });

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                    MainActivity.this,
                    drawerLayout, toolbar,
                    R.string.open_drawer,
                    R.string.close_drawer
            );
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    String url = null;
                    int id = menuItem.getItemId();

                    if (id == R.id.linked_in) {
                        url = "https://www.linkedin.com/in/bishal-sarma-41a9b128a?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app";
                    } else if (id == R.id.insta_gram) {
                        url = "https://www.instagram.com/___b1shal_svs";
                    } else if (id == R.id.face_book) {
                        url = "https://www.facebook.com/vishal.jimon?mibextid=ZbWKwL";
                    } else if (id == R.id.git_hub) {
                        url = "https://github.com/svsBishal";
                    } else {
                        url = "https://drive.google.com/file/d/1Snf2ZY6cTbSsI3JcawRezYwbA0LkYXK7/view?usp=drivesdk";
                    }

                    if (url != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }

                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });

        }

    }


    private void startNewGame() {
        // Set initial number of attempts
        attempts_left_count = 5;
        // Set initial points
        points_remaining_count = 100;
        // Set reset button text
        reset_btn.setText("Reset");
        // Set isHintUsed
        isHintUsed = false;
        // Set guess_btn, editText, hint_text visible
        hint_text.setVisibility(View.VISIBLE);
        guess_btn.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        // Set hint text
        hint_text.setText("");
        // Set l_animation
        setAnimation(R.raw.lose);

        // Set colours and text
        result_text.setText("New game started!");
        attempts_left_num.setText(String.valueOf(attempts_left_count));
        points_rem_num.setText(String.valueOf(points_remaining_count));
        total_attempts_num.setText(String.valueOf(5));

        editText.setText("");
        points_text.setText("total points");
        total_attempts_text.setText("total attempts");
        total_left_text.setText("attempts left");

        points_text.setTextColor(Color.WHITE);
        total_attempts_text.setTextColor(Color.WHITE);
        total_left_text.setTextColor(Color.WHITE);
        points_rem_num.setTextColor(Color.WHITE);
        result_text.setTextColor(Color.WHITE);
        attempts_left_num.setTextColor(Color.WHITE);
        total_attempts_num.setTextColor(Color.WHITE);

        options[0].setTextColor(Color.parseColor("#ECFF0D"));
        options[1].setTextColor(Color.parseColor("#C30A0A"));
        options[2].setTextColor(Color.parseColor("#094EFF"));
        options[3].setTextColor(Color.parseColor("#D508CD"));
        options[4].setTextColor(Color.parseColor("#22BF08"));
        options[5].setTextColor(Color.parseColor("#FF7F09"));

        reset_btn.setBackgroundResource(R.drawable.reset_btn);
        hints_btn.setBackgroundResource(R.drawable.hint_btn);

        logo_white = ContextCompat.getDrawable(MainActivity.this, R.drawable.group_1);
        logo.setImageDrawable(logo_white);

        // Generate random values
        Random random = new Random();

        option_number[0] = random.nextInt(100) + 1;
        option_number[1] = random.nextInt(100) + 1;
        option_number[2] = random.nextInt(100) + 1;
        option_number[3] = random.nextInt(100) + 1;
        option_number[4] = random.nextInt(100) + 1;
        option_number[5] = random.nextInt(100) + 1;

        // Testing purpose only
        options[0].setText(String.valueOf(option_number[0]));
        options[1].setText(String.valueOf(option_number[1]));
        options[2].setText(String.valueOf(option_number[2]));
        options[3].setText(String.valueOf(option_number[3]));
        options[4].setText(String.valueOf(option_number[4]));
        options[5].setText(String.valueOf(option_number[5]));

        // To chhose a number randomly b/w the options
        target_index = random.nextInt(5);

    }

    private void judgement() {
        String inputString = editText.getText().toString();
        // int userInput = Integer.parseInt(inputString);
        if (inputString.isEmpty()) {
            Toast.makeText(MainActivity.this, "Enter a number", Toast.LENGTH_SHORT).show();
            return;
        }
        int userInput = Integer.parseInt(inputString);
        if (userInput < 1 || userInput > 100) {
            Toast.makeText(MainActivity.this, "Invalid input!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userInput == option_number[target_index]) {
            result_text.setText("Congrats!\nCorrect number was " + option_number[target_index]);
            attempts_left_num.setText(String.valueOf(attempts_left_count = 0));
            reset_btn.setText("Start");
            hint_text.setVisibility(View.INVISIBLE);
            guess_btn.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);

            // If matched every option will be changed
            for (int i = 0; i < 6; i++) {
                options[i].setText(String.valueOf(points_remaining_count));
                options[i].setTextColor(Color.parseColor("#FFD700"));
            }

            attempts_left_num.setText(String.valueOf(points_remaining_count));
            total_attempts_num.setText(String.valueOf(points_remaining_count));

            points_text.setText("Your final score");
            total_attempts_text.setText("Your final score");
            total_left_text.setText("Your final score");

            points_text.setTextColor(Color.parseColor("#FFD700"));
            total_attempts_text.setTextColor(Color.parseColor("#FFD700"));
            total_left_text.setTextColor(Color.parseColor("#FFD700"));
            points_rem_num.setTextColor(Color.parseColor("#FFD700"));
            result_text.setTextColor(Color.parseColor("#FFD700"));
            attempts_left_num.setTextColor(Color.parseColor("#FFD700"));
            total_attempts_num.setTextColor(Color.parseColor("#FFD700"));

            reset_btn.setBackgroundResource(R.drawable.golden_reset);
            hints_btn.setBackgroundResource(R.drawable.golden_hint);

            gold_logo = ContextCompat.getDrawable(MainActivity.this, R.drawable.golden_logo);
            logo.setImageDrawable(gold_logo);

            setAnimation(R.raw.lose);


            return;

        } else {
            result_text.setText("Try again!");
            setAnimation(R.raw.win);

            attempts_left_count--;
            attempts_left_num.setText(String.valueOf(attempts_left_count));

            points_remaining_count = points_remaining_count - 15;
            points_rem_num.setText(String.valueOf(points_remaining_count));
        }

        if (attempts_left_count <= 0) {
            result_text.setText("Game over!!\nCorrect number is " + option_number[target_index]);
            attempts_left_num.setText("0");
            reset_btn.setText("Start");
            hint_text.setVisibility(View.INVISIBLE);
            guess_btn.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);

            // If over every option will be changed
            for (int i = 0; i < 6; i++) {
                options[i].setText(String.valueOf(option_number[target_index]));
            }

            // If lost
            points_remaining_count = 0;
            for (int i = 0; i < 6; i++) {
                options[i].setText(String.valueOf(points_remaining_count));
                options[i].setTextColor(Color.WHITE);
            }

            attempts_left_num.setText(String.valueOf(points_remaining_count));
            total_attempts_num.setText(String.valueOf(points_remaining_count));
            points_rem_num.setText(String.valueOf(points_remaining_count));

            points_text.setText("Your final score");
            total_attempts_text.setText("Your final score");
            total_left_text.setText("Your final score");

            points_text.setTextColor(Color.WHITE);
            total_attempts_text.setTextColor(Color.WHITE);
            total_left_text.setTextColor(Color.WHITE);
            points_rem_num.setTextColor(Color.WHITE);
            result_text.setTextColor(Color.WHITE);
            attempts_left_num.setTextColor(Color.WHITE);
            total_attempts_num.setTextColor(Color.WHITE);

            setAnimation(R.raw.win);

        }

        if(attempts_left_count != 0 && attempts_left_count != 5) {
            points_text.setText("points remaining");
            editText.setText("");
        }
    }

    private void makeHints() {
        if(option_number[target_index]%2 == 0)
            hint_text.setText("  Number is even");
        if(option_number[target_index]%3 == 0)
            hint_text.setText("  Number is divisible by 3");
        if(option_number[target_index]%5 == 0)
            hint_text.setText("  Number is divisible by 5");
        if(option_number[target_index]%7 == 0)
            hint_text.setText("  Number is divisible by 7");
        if(option_number[target_index]%11 == 0)
            hint_text.setText("  Number is divisible by 11");
        if(option_number[target_index]%13 == 0)
            hint_text.setText("  Number is divisible by 13");
        if(option_number[target_index]%17 == 0)
            hint_text.setText("  Number is divisible by 17");
        if(option_number[target_index]%23 == 0)
            hint_text.setText("  Number is divisible by 23");
    }

    private void setAnimation(int animationResource) {
        animation.setAnimation(animationResource);
        animation.playAnimation();
        animation.loop(true);
    }


    @Override
    public void onBackPressed() {
        if(attempts_left_count > 0 && attempts_left_count != 5) {
            AlertDialog.Builder exitDailog = new AlertDialog.Builder(MainActivity.this);
            exitDailog.setTitle("Exit?");
            exitDailog.setCancelable(false);
            exitDailog.setMessage("Your game isn't over yet.\nAre you sure to exit?");
            exitDailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });

            exitDailog.setNegativeButton("No", null);

            exitDailog.show();
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {MainActivity.super.onBackPressed();}
    }

}


