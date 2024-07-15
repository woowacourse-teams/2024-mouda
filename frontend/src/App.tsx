import MoimInput from './components/Input/MoimInput';
import { Input_Info_List } from './constants';
import { Global } from '@emotion/react';
import reset from './reset';

export default function App() {
  return (
    <>
      <Global styles={reset} />
      <MoimInput data={Input_Info_List['title']} />
    </>
  );
}
