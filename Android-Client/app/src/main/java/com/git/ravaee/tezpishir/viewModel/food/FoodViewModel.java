package com.git.ravaee.tezpishir.viewModel.food;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.git.ravaee.tezpishir.model.Food;
import com.git.ravaee.tezpishir.model.Group;
import com.git.ravaee.tezpishir.model.response.FoodResponse;
import com.git.ravaee.tezpishir.repositories.FoodRepository;
import com.git.ravaee.tezpishir.root.App;

public class FoodViewModel extends ViewModel {

    private MutableLiveData<FoodResponse> data;
    private FoodRepository foodRepository;

    public FoodViewModel(App app) {
        foodRepository = new FoodRepository(app);
    }

    public MutableLiveData<FoodResponse> getFoodList(Group group, int pageNumber, int count){

        data = foodRepository.getFoodList(group,pageNumber,count);
        return data;
    }

    public MutableLiveData<FoodResponse> getFoodDetails(Food food){

        data = foodRepository.getFoodDetails(food);
        return data;
    }

}
