import MoimInput from './components/Input/MoimInput';
import { Input_Info_List } from './constants';
import { Global } from '@emotion/react';
import Button from './components/Button/Button';
import reset from './reset';
import MoimCardList from './components/MoimCardList/MoimCardList';

import MoimRegister from './layouts/MoimRegister/MoimRegister';

import Plus from './common/assets/tabler_plus.svg';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import ENDPOINTS from './constants/endpoints';
import TmpAddMoim from './pages/TmpAddMoim';
import TmpMain from './pages/TmpMain';
import { useMemo } from 'react';

const router = createBrowserRouter([
  {
    path: ENDPOINTS.main,
    element: <TmpMain />,
  },
  {
    path: ENDPOINTS.addMoim,
    element: <TmpAddMoim />,
  },
]);

export default function App() {
  const queryClient = useMemo(() => {
    return new QueryClient();
  }, []);

  return (
    <>
      <Global styles={reset} />
      <MoimInput data={Input_Info_List['title']} />
      <MoimCardList data={mockdata} />
      <Button shape="circle">
        <img src={Plus}></img>
      </Button>
      <Button shape="bar">등록하기</Button>
      <MoimRegister />
      <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
      </QueryClientProvider>
    </>

  );
}
