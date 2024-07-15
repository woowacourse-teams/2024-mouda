import { Global } from '@emotion/react';
import reset from './common/reset.style';
// import MoimList from './layouts/MoimList/MoimList';
import MoimRegister from './layouts/MoimRegister/MoimRegister';

export default function App() {
  return (
    <>
      <Global styles={reset} />
      {/* <MoimList /> */}
      <MoimRegister />
    </>
  );
}
