import MoimCardList from '@_components/MoimCardList/MoimCardList';
import useMoims from '@_hooks/queries/useMoims';

export default function MoimList() {
  const { moims, isLoading } = useMoims();

  if (isLoading) {
    return <div>로딩중...</div>;
  }

  return moims && <MoimCardList moimInfos={moims} />;
}
