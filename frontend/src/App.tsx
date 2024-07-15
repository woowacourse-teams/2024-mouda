import { Global } from '@emotion/react';
import MoimList from './layouts/MoimList';
import reset from './common/reset.style';

export default function App() {
  return (
    <>
      <Global styles={reset} />
      <MoimList />
    </>
  );
}
