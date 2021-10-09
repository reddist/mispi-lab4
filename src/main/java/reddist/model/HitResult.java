package reddist.model;

import lombok.Getter;

public class HitResult {
    private String stringX;
    private String stringY;
    private String stringR;
    private String result;
    @Getter
    private double x;
    @Getter
    private double y;
    @Getter
    private double r;
    private boolean valid = true;

    public HitResult(HitBase hit) {
        this.stringX = hit.getX();
        this.stringY = hit.getY();
        this.stringR = hit.getR();
        checkArea();
    }

    public HitResult(String stringX, String stringY, String stringR) {
        this.stringX = stringX;
        this.stringY = stringY;
        this.stringR = stringR;
        checkArea();
    }

    private void checkArea() {
        if(!checkParams()) return;

        boolean checkResult = false;

        // triangle
        if(x >= 0 && y <= 0) {
            checkResult = y >= (x - r / 2.0);
        }

        // circle
        if(x >= 0 && y >= 0) {
            checkResult = checkResult || ((x*x + y*y) <= (r*r / 4.0D));
        }

        // rectangle
        if(x <= 0 && y >= 0) {
            checkResult = checkResult || (y <= r) && ( x >= -r);
        }

        // whitespace
        checkResult &= !(x < 0 && y < 0);

        if(checkResult){
            result = "Вы попали";
        } else {
            result = "Вы не попали";
        }
    }

    private boolean checkParams(){
        boolean returned = true;
        try {
            x = Double.parseDouble(stringX);
            if(x > 7.0 || x < -7.0) {
                result = "Некорректное значение x";
                returned = false;
            }
        } catch (NumberFormatException e) {
            result = "Некорректный формат x";
            valid = false;
            return false;
        }
        try {
            y = Double.parseDouble(stringY);
            if(y > 7.0 || y < -7.0) {
                result = "Некорректное значение y";
                returned = false;
            }
        } catch (NumberFormatException e) {
            result = "Некорректный формат y";
            valid = false;
            return false;
        }
        try {
            r = Double.parseDouble(stringR);
            if(r > 5.0 || r < 0.0) {
                result = "Некорректное значение r";
                returned = false;
            }
        } catch (NumberFormatException e) {
            result = "Некорректный формат r";
            valid = false;
            return false;
        }
        return returned;
    }

    @Override
    public String toString() {
        return "HitResult{" +
                "stringX='" + stringX + '\'' +
                ", stringY='" + stringY + '\'' +
                ", stringR='" + stringR + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public String getStringX() {
        return stringX;
    }

    public String getStringY() {
        return stringY;
    }

    public String getStringR() {
        return stringR;
    }

    public String getResult() {
        return result;
    }

    public boolean isValid() {
        return valid;
    }
}
