import { Global } from '@emotion/react';
import reset from './common/reset.style';
import MoimListPage from './pages/MoimList';

export default function App() {
  return (
    <>
      <Global styles={reset} />
      {/* <MoimInput data={Input_Info_List['title']} />
      <MoimCardList data={mockdata} />
      <Button shape="bar">{PLUS}</Button> */}
      <MoimListPage />
    </>
  );
}
