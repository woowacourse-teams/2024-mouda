import PleaseCardList from '@_components/PleaseCardList/PleaseCardList';
import usePleases from '@_hooks/queries/usePleases';

export default function PleaseList() {
  const { pleases, isLoading } = usePleases();

  if (isLoading) {
    return <div>로딩중...</div>;
  }

  return pleases && <PleaseCardList pleases={pleases} />;
}
