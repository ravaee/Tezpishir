db.foods.find().forEach(function (x) {
    delete x.likedBy 
    db.foods.save(x);
});