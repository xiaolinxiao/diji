package com.zrdz.diji.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.zrdz.diji.R;

import java.lang.reflect.Field;

/**
 * A simple dialog containing an {@link DatePicker}.
 * <p>
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 * </p>
 */
public class MyDatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {

    private static final String START_YEAR = "start_year";
    private static final String END_YEAR = "end_year";
    private static final String START_MONTH = "start_month";
    private static final String END_MONTH = "end_month";
    private static final String START_DAY = "start_day";
    private static final String END_DAY = "end_day";

    private final DatePicker mDatePicker_start;
    private final OnDateSetListener mCallBack;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {

        /**
         * @param startDatePicker  The view associated with this listener.
         * @param startYear        The year that was set.
         * @param startMonthOfYear The month that was set (0-11) for compatibility with
         *                         {@link java.util.Calendar}.
         * @param startDayOfMonth  The day of the month that was set.
         */
        void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth);
    }

    /**
     * @param context     The context the dialog is to run in.
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     */
    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth);
    }

    public MyDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth, true, 0);
    }

    /**
     * @param context     The context the dialog is to run in.
     * @param theme       the theme to apply to this dialog
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     */
    public MyDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth, boolean isDayVisible, int type) {
        super(context, theme);

        mCallBack = callBack;

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        // setButton(BUTTON_POSITIVE,
        // themeContext.getText(android.R.string.date_time_done), this);
        setIcon(0);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker_dialog, null);
        setView(view);
        mDatePicker_start = (DatePicker) view.findViewById(R.id.datePickerStart);
        // mDatePicker_end = (DatePicker) view.findViewById(R.id.datePickerEnd);
        mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
        // mDatePicker_end.init(year, monthOfYear, dayOfMonth, this);
        // updateTitle(year, monthOfYear, dayOfMonth);

        // 如果要隐藏当前日期，则使用下面方法。
        if (!isDayVisible) {
            hidDay(mDatePicker_start, type);
            // hidDay(mDatePicker_end);
        }
    }

    /**
     * 隐藏DatePicker中的日期显示
     *
     * @param type       0：日隐藏  1：月日
     * @param datePicker
     */
    private void hidDay(DatePicker datePicker, int type) {
        // 利用java反射技术得到picker内部的属性，并对其进行操作
        Class<? extends DatePicker> c = datePicker.getClass();
        try {
            Field fd = null, fm = null, fy = null;
            // 系统版本大于5.0
            Log.e("=Build.VERSION.SDK_INT=", "===" + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT >= 21) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
                // int yearSpinnerId =
                // Resources.getSystem().getIdentifier("year", "id", "android");
                // 隐藏日
                // if (daySpinnerId != 0) {
                View daySpinner = datePicker.findViewById(daySpinnerId);
                View monthSpinner = datePicker.findViewById(monthSpinnerId);
                if (daySpinner != null) {
                    Log.e("====daySpinner======", "====daySpinner======");
                    daySpinner.setVisibility(View.GONE);
                }
                // 隐藏月日
                if (type == 1) {
                    if (monthSpinnerId != 0) {
                        monthSpinner.setVisibility(View.GONE);
                        return;
                    }
                } else {
                    Log.e("====daySpinner======", "====daySpinner==null====");
                    return;
                }

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                // 系统版本大于4.0
                fd = c.getDeclaredField("mDaySpinner");
                fm = c.getDeclaredField("mMonthSpinner");
                fy = c.getDeclaredField("mYearSpinner");
            } else {
                fd = c.getDeclaredField("mDayPicker");
                fm = c.getDeclaredField("mMonthPicker");
                fy = c.getDeclaredField("mYearPicker");
            }
            // 对字段获取设置权限
            fd.setAccessible(true);
            fm.setAccessible(true);
            fy.setAccessible(true);
            // 得到对应的控件
            View vd = (View) fd.get(datePicker);
            View vm = (View) fm.get(datePicker);
            View vy = (View) fy.get(datePicker);
            vd.setVisibility(View.GONE);
            vm.setVisibility(View.VISIBLE);
            vy.setVisibility(View.VISIBLE);
            if (type == 1) {
                vd.setVisibility(View.GONE);
                vm.setVisibility(View.GONE);
                vy.setVisibility(View.VISIBLE);
            } else {
//                vd.setVisibility(View.VISIBLE);
                vm.setVisibility(View.VISIBLE);
                vy.setVisibility(View.VISIBLE);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void onClick(DialogInterface dialog, int which) {
        // Log.d(this.getClass().getSimpleName(), String.format("which:%d",
        // which));
        // 如果是“取 消”按钮，则返回，如果是“确 定”按钮，则往下执行
        if (which == BUTTON_POSITIVE)
            tryNotifyDateSet();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        if (view.getId() == R.id.datePickerStart)
            mDatePicker_start.init(year, month, day, this);
        // if (view.getId() == R.id.datePickerEnd)
        // mDatePicker_end.init(year, month, day, this);
        // // updateTitle(year, month, day);
    }

    /**
     * 获得开始日期的DatePicker
     *
     * @return The calendar view.
     */
    public DatePicker getDatePickerStart() {
        return mDatePicker_start;
    }

    /**
     * 获得结束日期的DatePicker
     *
     * @return The calendar view.
     */
    public DatePicker getDatePickerEnd() {
        // return mDatePicker_end;
        return null;
    }

    /**
     * Sets the start date.
     *
     * @param year        The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth  The date day of month.
     */
    public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
    }

    /**
     * Sets the end date.
     *
     * @param year        The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth  The date day of month.
     */
    public void updateEndDate(int year, int monthOfYear, int dayOfMonth) {
        // mDatePicker_end.updateDate(year, monthOfYear, dayOfMonth);
    }

    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mDatePicker_start.clearFocus();
            // mDatePicker_end.clearFocus();
            mCallBack.onDateSet(mDatePicker_start, mDatePicker_start.getYear(), mDatePicker_start.getMonth(), mDatePicker_start.getDayOfMonth());
        }
    }

    @Override
    protected void onStop() {
        // tryNotifyDateSet();
        super.onStop();
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(START_YEAR, mDatePicker_start.getYear());
        state.putInt(START_MONTH, mDatePicker_start.getMonth());
        state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
        // state.putInt(END_YEAR, mDatePicker_end.getYear());
        // state.putInt(END_MONTH, mDatePicker_end.getMonth());
        // state.putInt(END_DAY, mDatePicker_end.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int start_year = savedInstanceState.getInt(START_YEAR);
        int start_month = savedInstanceState.getInt(START_MONTH);
        int start_day = savedInstanceState.getInt(START_DAY);
        mDatePicker_start.init(start_year, start_month, start_day, this);

        int end_year = savedInstanceState.getInt(END_YEAR);
        int end_month = savedInstanceState.getInt(END_MONTH);
        int end_day = savedInstanceState.getInt(END_DAY);
        // mDatePicker_end.init(end_year, end_month, end_day, this);
    }
}
