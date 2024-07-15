import MoimInput from './components/Input/MoimInput';
import { Input_Info_List } from './constants';
import { Global } from '@emotion/react';
import Button from './components/Button/Button';
import reset from './reset';
import MoimCardList from './components/MoimCardList/MoimCardList';
import { PLUS } from './common/assets';
import { Global } from '@emotion/react';
import reset from './common/reset.style';
import MoimList from './layouts/MoimList/MoimList';
import MoimRegister from './layouts/MoimRegister/MoimRegister';
export default function App() {
  const mockdata = [
    {
      title: '축구 하실 사람?',
      place: '서울 마포구 독막로 96 1층',
      date: '7월 15일',
      time: '오후 2시',
      maxPeople: 4,
    },
    {
      title: '축구 하실 사람?',
      place: '서울 마포구 독막로 96 1층',
      date: '7월 15일',
      time: '오후 2시',
      maxPeople: 4,
    },
    {
      title: '축구 하실 사람?',
      place: '서울 마포구 독막로 96 1층',
      date: '7월 15일',
      time: '오후 2시',
      maxPeople: 4,
    },
    {
      title: '축구 하실 사람?',
      place: '서울 마포구 독막로 96 1층',
      date: '7월 15일',
      time: '오후 2시',
      maxPeople: 4,
    },
  ];
  return (
    <>
      <Global styles={reset} />
      <MoimInput data={Input_Info_List['title']} />
      <MoimCardList data={mockdata} />
      <Button shape="bar">{PLUS}</Button>
      <MoimRegister />
    </>
  );
}
