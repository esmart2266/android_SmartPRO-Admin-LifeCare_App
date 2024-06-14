package in.esmartsolution.shree.proadmin.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.esmartsolution.shree.proadmin.R;
import in.esmartsolution.shree.proadmin.model.Visit;
import in.esmartsolution.shree.proadmin.model.VisitMain;
import in.esmartsolution.shree.proadmin.widget.expandablerecyclerview.ExpandableRecyclerAdapter;

/**
 * Created by mayur on 7/1/2016.
 */
public class VisitExpandableAdapter extends ExpandableRecyclerAdapter<VisitMain, Visit, VisitPViewHolder, VisitCViewHolder> {
    //    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private Context mContext;
    private List<VisitMain> mainList;

    public VisitExpandableAdapter(Context mContext, List<VisitMain> mainList) {
        super(mainList);
        this.mContext = mContext;
        this.mainList = mainList;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<VisitMain> getMainList() {
        return mainList;
    }

    @UiThread
    @NonNull
    @Override
    public VisitPViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {
            default:
            case PARENT_NORMAL:
                recipeView = mInflater.inflate(R.layout.header_view, parentViewGroup, false);
                break;
//            case PARENT_VEGETARIAN:
//                recipeView = mInflater.inflate(R.layout.product_row, parentViewGroup, false);
//                break;
        }
        return new VisitPViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public VisitCViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        switch (viewType) {
            default:
            case CHILD_NORMAL:
                ingredientView = mInflater.inflate(R.layout.list_item, childViewGroup, false);
                break;
//            case CHILD_VEGETARIAN:
//                ingredientView = mInflater.inflate(R.layout.vegetarian_ingredient_view, childViewGroup, false);
//                break;
        }
        return new VisitCViewHolder(ingredientView, mContext);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull VisitPViewHolder recipeViewHolder, int parentPosition, @NonNull VisitMain recipe) {
        recipeViewHolder.bind(recipe);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull VisitCViewHolder ingredientViewHolder, int parentPosition, int childPosition, @NonNull Visit ingredient) {
        ingredientViewHolder.bind(ingredient);
    }

    @Override
    public int getParentViewType(int parentPosition) {
//        if (mRecipeList.get(parentPosition).isVegetarian()) {
//            return PARENT_VEGETARIAN;
//        } else {
        return PARENT_NORMAL;
//        }
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
//        Product ingredient = mRecipeList.get(parentPosition).getIngredient(childPosition);
//        if (ingredient.isVegetarian()) {
//            return CHILD_VEGETARIAN;
//        } else {
        return CHILD_NORMAL;
//        }
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_NORMAL;
    }
}