import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useToast } from "vue-toastification";
import { useRouter } from "vue-router";

export const useCounterStore = defineStore("main", () => {
  const role = ref<string>("");
  const toast = useToast();
  const router = useRouter();

  const login = async (email: string, password: string) => {
    const obj = {
      email: email,
      password: password,
    };
    try {
      const res = await axios.post(`/api/auth/login`, obj);
      if (res.status !== 200) {
        toast.error(res.data.message || "Login failed. Please try again.");
        return;
      }
      localStorage.setItem("token", res.data.token);
      role.value = res.data.role;
      toast.success("Login Successfully.");
      router.push("/home");
    } catch (error: any) {
      toast.error(
        error.response.data.message ||
          "Email or Password error.Please try again.!"
      );
    }
  };

  const signUp = async (email: string, password: string) => {
    const obj = {
      email: email,
      password: password,
    };
    try {
      const res = await axios.post(`/api/auth/sign-up`, obj);
      if (res.status !== 200) {
        toast.error(res.data.message || "Sign up failed. Please try again.");
        return;
      }
      toast.success("Sign up successfully. Please login back.");
      router.push("/");
    } catch (error: any) {
      toast.error(
        error.response.data.message || "There was an error.Please try again.!"
      );
    }
  };

  const isAdmin = () => {
    return role.value === "ADMIN";
  };

  const logout = () => {
    (role.value = ""), localStorage.removeItem("token");
    router.push("/");
  };

  return { isAdmin, login, logout, signUp };
});
