import { connect } from "react-redux";
import '../../../css/Pages/Account/User.css'


const User = (props) => {

  const func = () => {
    console.log("lol");
    console.log(props.id);
  }

  return (
    <div className="user-page">
      <h2>{props.id}</h2>
      <h3>{props.userName}</h3>
      <h3>{props.email}</h3>
      <input
      type="submit"
      onClick={func()}>
      </input>
    </div>
  )
}

const mapStateToProps = state => ({
  id: state.user.user.id,
  userName: state.user.user.userName,
  email: state.user.user.email,
  passwordHash: state.user.user.passwordHash,
  login: state.user.user.login,
  role: state.user.user.role,
});

export default connect(mapStateToProps)(User);