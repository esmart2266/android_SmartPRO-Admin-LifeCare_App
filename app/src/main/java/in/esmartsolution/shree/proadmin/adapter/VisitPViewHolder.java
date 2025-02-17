package in.esmartsolution.shree.proadmin.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import in.esmartsolution.shree.proadmin.R;
import in.esmartsolution.shree.proadmin.Singleton;
import in.esmartsolution.shree.proadmin.UserVisitsMapsActivity;
import in.esmartsolution.shree.proadmin.model.VisitMain;
import in.esmartsolution.shree.proadmin.widget.expandablerecyclerview.ParentViewHolder;


public class VisitPViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    @NonNull
    private final ImageView mArrowExpandImageView, iv_user_visits;
    private TextView mRecipeTextView;

    public VisitPViewHolder(@NonNull View itemView) {
        super(itemView);
        mRecipeTextView = (TextView) itemView.findViewById(R.id.tv_title);

        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow_expand_imageview);
        iv_user_visits = (ImageView) itemView.findViewById(R.id.iv_user_visits);
    }

    public void bind(@NonNull VisitMain visit) {
        String header = visit.getFirstName() + " " + visit.getLastName() + "(" + visit.getVisitList().size() + ")";
        mRecipeTextView.setText(header);
        iv_user_visits.setOnClickListener(view -> {
            Singleton.getInstance().setVisitMain(visit);
            itemView.getContext().startActivity(new Intent(itemView.getContext(),
                    UserVisitsMapsActivity.class)
                    .putExtra("user_name", header));
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {
                mArrowExpandImageView.setRotation(ROTATED_POSITION);
            } else {
                mArrowExpandImageView.setRotation(INITIAL_POSITION);
            }
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            mArrowExpandImageView.startAnimation(rotateAnimation);
        }
    }
}