package com.example.spikeawarebeta;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BarChartViewSimple extends View {

    private String[] labels = new String[0];
    private int[] values = new int[0];

    private final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BarChartViewSimple(Context context) { super(context); init(); }
    public BarChartViewSimple(Context context, AttributeSet attrs) { super(context, attrs); init(); }

    private void init() {
        axisPaint.setColor(0xFF444444);
        axisPaint.setStrokeWidth(3f);

        barPaint.setColor(0xFF8E2DE2); // purple

        textPaint.setColor(0xFF222222);
        textPaint.setTextSize(28f);

        gridPaint.setColor(0x22000000);
        gridPaint.setStrokeWidth(2f);
    }

    public void setData(String[] labels, int[] values) {
        this.labels = labels != null ? labels : new String[0];
        this.values = values != null ? values : new int[0];
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        int padL = 80, padR = 30, padT = 20, padB = 60;
        int chartW = w - padL - padR;
        int chartH = h - padT - padB;

        // axes
        canvas.drawLine(padL, padT, padL, padT + chartH, axisPaint);
        canvas.drawLine(padL, padT + chartH, padL + chartW, padT + chartH, axisPaint);

        if (values.length == 0) return;

        int max = 1;
        for (int v : values) max = Math.max(max, v);

        // y ticks + grid
        int ticks = 4;
        for (int t = 0; t <= ticks; t++) {
            float frac = t / (float) ticks;
            int val = Math.round(frac * max);
            float y = padT + chartH - (frac * chartH);

            canvas.drawText(String.valueOf(val), 8, y + 10, textPaint);
            canvas.drawLine(padL, y, padL + chartW, y, gridPaint);
        }

        int n = values.length;
        float stepX = (float) chartW / n;
        float barW = stepX * 0.6f;

        for (int i = 0; i < n; i++) {
            float xCenter = padL + (i + 0.5f) * stepX;
            float left = xCenter - barW / 2f;
            float right = xCenter + barW / 2f;

            float ratio = values[i] / (float) max;
            float top = padT + chartH - (ratio * chartH);
            float bottom = padT + chartH;

            canvas.drawRect(left, top, right, bottom, barPaint);

            // labels: if too many points, only show some
            int jump = Math.max(1, n / 6);
            if (labels.length == n && (n <= 10 || i % jump == 0)) {
                canvas.drawText(labels[i], left, h - 18, textPaint);
            }
        }
    }
}
