package com.answer.library.JsonView.bean;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.enums.PropertyType;
import com.answer.library.JsonView.manager.AnimationManager;
import com.answer.library.JsonView.manager.ViewIdManager;
import com.answer.library.JsonView.utils.EmptyUtil;
import com.answer.library.JsonView.utils.ListUtil;

import java.util.List;
import com.answer.library.JsonView.manager.GsonSingleton;

public class ObjectAnimatorBean {

    public static final String TAG = "ObjectAnimatorBean";
    private static final Gson gson = GsonSingleton.getInstance();

    private String type = "ObjectAnimator";
    private String id;
    private String property = "";
    private String repeatMode = "RESTART";
    private String target;
    private List<Object> play;
    private List<Object> with;
    private List<Object> after;
    private List<Object> before;
    private List<String> values;
    private int time = 0;
    private int repeatCount;
    private boolean start = true;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(String repeatMode) {
        this.repeatMode = repeatMode;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<Object> getPlay() {
        return play;
    }

    public void setPlay(List<Object> play) {
        this.play = play;
    }

    public List<Object> getWith() {
        return with;
    }

    public void setWith(List<Object> with) {
        this.with = with;
    }

    public List<Object> getAfter() {
        return after;
    }

    public void setAfter(List<Object> after) {
        this.after = after;
    }

    public List<Object> getBefore() {
        return before;
    }

    public void setBefore(List<Object> before) {
        this.before = before;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    // Start animation method
    public ObjectAnimator start(View view) {
        AnimatorSet animatorSet = new AnimatorSet();

        if ("ObjectAnimator".equals(getType())) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(
                ViewIdManager.get(getTarget()),
                PropertyType.valueOf(getProperty()).getProperty(),
                ListUtil.listToFloatArray(getValues())
            );

            if (getTime() != 0) {
                animatorSet.setDuration(getTime());
            } else {
                animator.setDuration(0);
            }

            animator.setRepeatMode("RESTART".equals(getRepeatMode()) ? ValueAnimator.RESTART : ValueAnimator.REVERSE);
            animator.setRepeatCount(getRepeatCount());
            animatorSet.play(animator);
            animatorSet.setInterpolator(new LinearInterpolator());

            if (isStart()) {
                animatorSet.start();
            }

            if (getId() != null) {
                AnimationManager.add(getId(), animator);
            }

            return animator;
        } else if ("AnimatorSet".equals(getType())) {
            AnimatorSet.Builder animatorSetPlay = null;

            if (EmptyUtil.isNotNull(getPlay())) {
                for (Object playItem : getPlay()) {
                    ObjectAnimatorBean bean = gson.fromJson(((JsonObject) playItem).toString(), ObjectAnimatorBean.class);
                    animatorSetPlay = animatorSet.play(bean.start(view));
                }
            }

            if (EmptyUtil.isNotNull(getAfter()) && animatorSetPlay != null) {
                for (Object afterItem : getAfter()) {
                    ObjectAnimatorBean bean = gson.fromJson(((JsonObject) afterItem).toString(), ObjectAnimatorBean.class);
                    animatorSetPlay.after(bean.start(view));
                }
            }

            if (EmptyUtil.isNotNull(getBefore()) && animatorSetPlay != null) {
                for (Object beforeItem : getBefore()) {
                    ObjectAnimatorBean bean = gson.fromJson(((JsonObject) beforeItem).toString(), ObjectAnimatorBean.class);
                    animatorSetPlay.before(bean.start(view));
                }
            }
        }

        return null;
    }
}

