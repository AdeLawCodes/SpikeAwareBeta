package com.example.spikeawarebeta;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LineChartView extends View {

    private String[] labels = new String[0];
    private int[] values = new int[0];

    private final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public LineChartView(Context context) { super(context); init(); }
    public LineChartView(Context context, AttributeSet attrs) { super(context, attrs); init(); }

    private void init() {
        axisPaint.setColor(0xFF444444);
        axisPaint.setStrokeWidth(3f);

        linePaint.setColor(0xFF8E2DE2); // Spike purple
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);

        pointPaint.setColor(0xFF8E2DE2);

        textPaint.setColor(0xFF222222);
        textPaint.setTextSize(28f);
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

        if (values.length < 2) return;

        int max = 1;
        for (int v : values) max = Math.max(max, v);

        // y-axis tick labels (0, 25, 50, 75, 100% of max)
        int ticks = 4; // gives 5 labels including 0
        for (int t = 0; t <= ticks; t++) {
            float frac = t / (float) ticks;
            int val = Math.round(frac * max);

            float y = padT + chartH - (frac * chartH);
            canvas.drawText(String.valueOf(val), 8, y + 10, textPaint);

            // light grid line (optional but helpful)
            Paint grid = new Paint(Paint.ANTI_ALIAS_FLAG);
            grid.setColor(0x22000000);
            grid.setStrokeWidth(2f);
            canvas.drawLine(padL, y, padL + chartW, y, grid);
        }


        float stepX = (float) chartW / (values.length - 1);

        float lastX = padL;
        float lastY = padT + chartH - ((values[0] / (float) max) * chartH);

        // draw line + points
        for (int i = 1; i < values.length; i++) {
            float x = padL + i * stepX;
            float y = padT + chartH - ((values[i] / (float) max) * chartH);

            canvas.drawLine(lastX, lastY, x, y, linePaint);
            canvas.drawCircle(x, y, 4.5f, pointPaint);

            lastX = x;
            lastY = y;
        }

        // x-axis labels (every ~6 points to avoid clutter)
        int jump = Math.max(1, values.length / 8);
        for (int i = 0; i < labels.length; i += jump) {
            float x = padL + i * stepX;
            canvas.drawText(labels[i], x - 28, h - 18, textPaint);
        }
    }
}
