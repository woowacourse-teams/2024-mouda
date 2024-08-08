import MissingFallback from '@_components/MissingFallback/MissingFallback';
import MoimCardList from '@_components/MoimCardList/MoimCardList';
import useMyZzimMoims from '@_hooks/queries/useMyZzimMoim';

export default function MyZzimMoimList() {
  const { myZzimMoims, isLoading } = useMyZzimMoims();

  if (isLoading) {
    return <div>로딩중...</div>;
  }

  return myZzimMoims && myZzimMoims.length > 0 ? (
    <MoimCardList moimInfos={myZzimMoims} />
  ) : (
    <MissingFallback text="아직 찜한 모임이 없어요" />
  );
}
