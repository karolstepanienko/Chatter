package com.chatter.model.User;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

// Project imports
import com.chatter.model.Post.Post;

// This tells Hibernate to make a table in database out of this class
// Hibernate automatically translates the entity into a table.
@Data
@Entity
@Table(	name = "user", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
})
public class User {

  /**
   * User ID. Used to identify user in database.
   * Generated automatically.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Integer id;

  /**
   * User userName. Unique amongst users,
   */
  private String userName;

  /**
   * User login not checked.
   */
  private String login;

  /**
   * User email.
   */
  private String email;

  /**
   * User password hash.
   * Created using BCrypt.
   */
  private String passwordHash;

  /**
   * User privacy setting.
   */
  private String accountPrivacy;

  /**
   * User account roles.
   */
	private String role;

  /**
   * Posts liked by user.
   */
  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE
  })
  @JoinTable(name = "user_post",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "post_id")
  )
  private Set<Post> posts;

  /**
   * Standard constructor.
   */
  public User() { }

  /**
   * Constructor with arguments.
   * @param newUserName User userName.
   * @param newLogin User login.
   * @param newEmail User email.
   * @param newPasswordHash User password hash.
   */
  public User(
    final String newUserName,
    final String newLogin,
    final String newEmail,
    final String newPasswordHash) {
    this.userName = newUserName;
    this.login = newLogin;
    this.email = newEmail;
    this.passwordHash = newPasswordHash;
  }

  /**
   * Adds post when user likes it.
   * @param post Post that was liked.
   */
  public void addPost(final Post post) {
    if (this.posts == null) {
      this.posts = new HashSet<Post>();
    }
    this.posts.add(post);
  }

  /**
   * Removes post when user unlikes it.
   * @param post Post to be removed.
   */
  public void removePost(final Post post) {
    this.posts.remove(post);
  }
}
