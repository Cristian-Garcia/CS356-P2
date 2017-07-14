package user;

import observer.Observer;
import observer.Subject;
import tree.UserElementTreeModel;
import visitor.Visitor;
import java.util.ArrayList;

public class User extends Subject implements Observer, UserElement{
    private UserElementTreeModel treeModel;                         
    private String uniqueID;                                       
    private ArrayList<String> tweets;                              
    private ArrayList<String> newsFeed;                             
    private UserView userView;                                      
    private final String iconURL = "/resources/person.png";      
    private ArrayList<User> following;                             
    
    public User(UserElementTreeModel treeModel, String uniqueID){
        this.treeModel = treeModel;
        this.uniqueID = uniqueID;
        
        userView = new UserView(this);
        tweets = new ArrayList();
        newsFeed = new ArrayList();
        following = new ArrayList();
        
        userView.init();
        attach(this);
        following.add(this);
    }
    
    public String getLatestTweet(){
        return tweets.get(tweets.size() - 1);
    }

    public ArrayList<String> getTweets(){
        return tweets;
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof User) {
            System.out.println("subject: " + ((User) subject).getUniqueID());
            postToNewsFeed(((User) subject).getUniqueID() + ": " + ((User) subject).getLatestTweet());
        }
    }

    public void postToNewsFeed(String tweet){
        System.out.println("tweet:" + tweet);
        newsFeed.add(tweet);
        userView.addTweetToNewsFeed(tweet);
    }

    public void postTweet(String tweet){
        tweets.add(tweet);
        notifyObservers();
    }
    
    public boolean followUser(String userID){
        User followUser = (User) treeModel.findUserByID((UserElement) treeModel.getRoot(), userID);        
        if (followUser != null && !following.contains(followUser)){
            followUser.attach(this);
            following.add(followUser);
            return true;
        }
        else {
            return false;
        }       
    }
    
    @Override
    public String toString(){
        return uniqueID;
    }   
    
    @Override
    public String getUniqueID(){
        return uniqueID;
    }

    @Override
    public void add(UserElement elem) {
        userView.errorMessage("Children Error", "Error: Users cannot have children.");
    }

    @Override
    public UserElement getChild(int i) {	
        return null;
    }

    @Override
    public int getIndexOfChild(UserElement elem) {    
        return -1;
    }

    @Override
    public int getChildCount() {     
        return 0;
    }

    @Override
    public void accept(Visitor vis) {
        vis.atUser(this);
    }   

    @Override
    public String getIconURL() {
        return iconURL;
    }
 
    @Override
    public void openUserView(){
        userView.setVisible(true);
    }
}
