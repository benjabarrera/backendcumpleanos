
import { RouterProvider } from 'react-router-dom';
import { router } from '../router';
import { ToastContainer } from '../../components/ui/Toast';

export const AppProviders = () => {
  return (
    <>
      <RouterProvider router={router} />
      <ToastContainer />
    </>
  );
};
