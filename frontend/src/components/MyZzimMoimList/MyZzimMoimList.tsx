import MoimCardList from '@_components/MoimCardList/MoimCardList';
import useMyZzimMoims from '@_hooks/queries/useMyZzimMoim';

export default function MyZzimMoimList() {
  const { myZzimMoims, isLoading } = useMyZzimMoims();

  if (isLoading) {
    return <div>로딩중...</div>;
  }

  return myZzimMoims && <MoimCardList moimInfos={myZzimMoims} />;
}
