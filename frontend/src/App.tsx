import { Global } from '@emotion/react';
import reset from './common/reset.style';
import MoimRegisterPage from './pages/MoimRegister';
// import MoimListPage from './pages/MoimList';

export default function App() {
  return (
    <>
      <Global styles={reset} />
      {/* <MoimListPage /> */}
      <MoimRegisterPage />
    </>
  );
}
