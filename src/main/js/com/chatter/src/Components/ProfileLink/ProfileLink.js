import { connect } from "react-redux";

const ProfileLink = (props) => {

  const display = () => {
    return ( props.id === null ?
      (null)
      :
      (<props.component to={"/profile"} className={props.className}>
        {props.message}
      </props.component>)
    );
  };

  return (display());
};

const mapStateToProps = state => ({
  id: state.user.user.id
});

export default connect(mapStateToProps)(ProfileLink);