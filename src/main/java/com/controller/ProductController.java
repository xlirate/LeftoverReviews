package com.controller;

import com.entities.Product;
import com.entities.RepoManager;
import com.entities.Review;
import com.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class ProductController {

    ///   /products?[productnamefrag={frag}]&[user={user id}]&[ordering={jaccard|bacon|rating|followedrating}]"
    @GetMapping("/products")
    public String productListDisplay(Model model,
                                     @CookieValue(name = "clientUserId", required = false) String clientUserId,
                                     @RequestParam(name = "productnamefrag", required = false) String fragment,
                                     @RequestParam(name = "user", required = false) String posterName,
                                     @RequestParam(name = "ordering", required = false) String ordering) {

        if(clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null){
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();

        List<Product> allProducts = new LinkedList<>();
        for(Product p : RepoManager.getProductRepository().findAll()){
            allProducts.add(p);
        }
        if(fragment != null && !"".equals(fragment)){
            Iterator<Product> ittr = allProducts.iterator();
            Product p = null;
            while(ittr.hasNext()){
                p = ittr.next();
                if(!p.getName().toUpperCase().contains(fragment.toUpperCase())){
                    ittr.remove();
                }
            }
        }

        if(posterName != null){
            Iterator<Product> ittr = allProducts.iterator();
            Product p;
            while(ittr.hasNext()){
                p = ittr.next();
                if(!p.getCreator().getUsername().equalsIgnoreCase(posterName)){
                    ittr.remove();
                }
            }
        }

        String[] orderings = {"jaccard", "bacon", "rating", "followedrating"};
        if(orderings[0].equalsIgnoreCase(ordering)){
            //jacard
            Collections.sort(allProducts, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Double.compare(o2.jacardWeightedScore(curentUser), o1.jacardWeightedScore(curentUser));
                }
            });
        }else if(orderings[1].equalsIgnoreCase(ordering)){
            //bacon

            Collections.sort(allProducts, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Double.compare(o2.baconWeightedScore(curentUser), o1.baconWeightedScore(curentUser));
                }
            });

        }else if(orderings[2].equalsIgnoreCase(ordering)){
            //rating

            Collections.sort(allProducts, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Double.compare(o2.averageScore(), o1.averageScore());
                }
            });

        }else if(orderings[3].equalsIgnoreCase(ordering)){
            //rating from followers

            Collections.sort(allProducts, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Double.compare(o2.averageFollowedScore(curentUser), o1.averageFollowedScore(curentUser));
                }
            });

        }else{
            Collections.shuffle(allProducts);
        }

        model.addAttribute("currentuser", curentUser);
        model.addAttribute("allproducts", allProducts);

        return "products";
    }

    @GetMapping("/products/{productid}")
    public String productDisplay(Model model,
        @PathVariable("productid") Long productId,
        @CookieValue(name = "clientUserId", required = false) String clientUserId,
        @RequestParam(name = "ordering", required = false, defaultValue = "") String ordering){

        if(clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null){
            return "redirect:/account";
        }
        User currentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        Product p = RepoManager.getProductRepository().findById(Long.valueOf(productId)).get();
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("product", p);

        List<Review> reviews = new ArrayList<>(p.getReviews());
        Collections.shuffle(reviews);


        if("jaccard".equalsIgnoreCase(ordering)){
            Collections.sort(reviews, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return Double.compare(currentUser.jaccardDistance(o1.getWriter()), currentUser.jaccardDistance(o2.getWriter()));
                }
            });
        }else if("bacon".equalsIgnoreCase(ordering)){
            Collections.sort(reviews, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return Double.compare(currentUser.baconDistance(o1.getWriter()), currentUser.baconDistance(o2.getWriter()));
                }
            });
        }else if("followed".equalsIgnoreCase(ordering)){
            Iterator<Review> itr = reviews.iterator();
            while(itr.hasNext()){
                if(!currentUser.getFollowedUsers().contains(itr.next().getWriter())){
                    itr.remove();
                }
            }
        }

        model.addAttribute("reviews", reviews);

        return "product";
    }

    @PostMapping("/products/review")
    public String addReview(Model model,
                            @CookieValue(name = "clientUserId", required = false) String clientUserId,
                            @RequestParam(name = "score") String score,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "productid") String productId) {
        if(clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null){
            return "redirect:/account";
        }
        User currentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        Product p = RepoManager.getProductRepository().findById(Long.valueOf(productId)).get();
        currentUser.writeReview(p,description,Double.valueOf(score));
        currentUser.save();
        return "redirect:/products/"+productId;
    }

    @PostMapping("/products/review/delete/{reviewid}")
    public String removeReview(Model model,
                               @PathVariable("reviewid") Long reviewId,
                               @CookieValue(name = "clientUserId", required = false) String clientUserId) {
        if(clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null){
            return "redirect:/account";
        }
        User currentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        Review review = RepoManager.getReviewRepository().findById(Long.valueOf(reviewId)).get();
        Long productId = review.getProduct().getId();

        if(review.getWriter().equals(currentUser)){
            review.getProduct().getReviews().remove(review);
            review.getWriter().getWritenReviews().remove(review);

            RepoManager.getReviewRepository().delete(review);
        }

        model.addAttribute("curentuser", currentUser);
        return "redirect:/products/"+productId;
    }

    //POST "/api/products" #create new product
    @PostMapping("/products")
    public String makeNewProduct(Model model,
                                 @CookieValue(name = "clientUserId", required = false) String clientUserId,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "url") String url,
                                 @RequestParam(name = "description") String desscription) {
        if(clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null){
            return "redirect:/account";
        }
        User currentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        Long productId = currentUser.createProduct(name, url,desscription).getId();
        currentUser.save();
        return "redirect:/products/"+productId;
    }

    //DELETE "/api/products/{product id}" # return to list of all products
    @PostMapping("/products/delete/{productid}")
    public String deleteProduct(Model model,
                                @PathVariable("productid") Long productId,
                                @CookieValue(name = "clientUserId", required = false) String clientUserId) {
        if(clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null){
            return "redirect:/account";
        }

        Product product = RepoManager.getProductRepository().findById(Long.valueOf(productId)).get();
        User currentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();

        if(product.getCreator().equals(currentUser)){

            RepoManager.getReviewRepository().deleteAll(product.getReviews());

            currentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
            currentUser.getCreatedProducts().remove(product);
            currentUser.save();
            RepoManager.getProductRepository().deleteById(Long.valueOf(productId));
        }

        return "redirect:/products";
    }
}