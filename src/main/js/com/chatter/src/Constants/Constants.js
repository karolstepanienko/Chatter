import axios from "axios";
import { useEffect } from "react";

export const link = 'http://localhost:8080/api';

export const validEmailRegex = RegExp(/^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i);

export const roles = {
  userRole: "USER",
  adminRole: "ADMIN"
}
