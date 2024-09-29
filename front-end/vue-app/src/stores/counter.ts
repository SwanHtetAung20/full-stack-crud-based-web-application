import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useToast } from "vue-toastification";
import { useRouter } from "vue-router";

export interface USER {
  id: string;
  name?: string;
  email: string;
  phone?: string;
  photo?: string;
  role?: string;
}

export const useCounterStore = defineStore("main", () => {
  const role = ref<string>("");
  const toast = useToast();
  const router = useRouter();

  const userList = ref<USER[]>([]);

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

  const findAll = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(`/api/admin/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 200) {
        toast.error(res.data.message || "Error Fetching userList.!");
        return;
      }
      toast.success("Successfully Displayed UserList.!");
      userList.value = res.data;
    } catch (error: any) {
      if (error.response && error.response.status === 403) {
        toast.error("Please login first.!"); // Handle 403 status
      } else {
        toast.error(
          error.response?.data.message ||
            "There was an error. Please try again.!"
        );
      }
    }
  };

  const deleteHandler = async (id: string) => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.delete(`/api/admin/delete/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 200) {
        toast.error(res.data.message || "Delete failed. Please try again.!");
        return;
      }
      toast.success(res.data);
      userList.value = userList.value.filter((user: USER) => user.id !== id);
    } catch (error: any) {
      toast.error(
        error.response.data.message || "There was an error.Please try again.!"
      );
    }
  };

  const updateHandler = async (id: string, obj: USER) => {
    try {
      const res = await axios.put(`/api/admin/update/${id}`, obj);
      if (res.status !== 200) {
        toast.error(res.data.message || "Update failed. Please try again.!");
        return;
      }
      toast.success(res.data.message || "Successfully updated.!");
      const index = userList.value.findIndex((user: USER) => user.id === id);
      if (index !== -1) {
        userList.value = res.data;
      }
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

  return {
    isAdmin,
    login,
    logout,
    signUp,
    findAll,
    updateHandler,
    deleteHandler,
    userList,
  };
});
