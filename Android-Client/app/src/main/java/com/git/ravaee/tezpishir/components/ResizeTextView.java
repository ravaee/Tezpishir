package com.git.ravaee.tezpishir.components;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatTextView;

public class ResizeTextView extends AppCompatTextView {

    public ResizeTextView(Context context) {
        super(context);
        this.convertTextSize();
    }

    public ResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.convertTextSize();
    }

    public ResizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.convertTextSize();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.convertTextSize();
    }

    private void convertTextSize(){
        if(this.getText().length() > 25){
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        }else{
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        }
    }
}
