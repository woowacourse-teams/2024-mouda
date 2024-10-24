import MissingFallback from '@_components/MissingFallback/MissingFallback';
import PleaseCardList from '@_components/PleaseCardList/PleaseCardList';
import usePleases from '@_hooks/queries/usePleases';
import PleaseCardListSkeleton from './PleaseCardListSkeleton/PleaseCardListSkeleton';

export default function PleaseList() {
  const { pleases, isLoading } = usePleases();

  if (isLoading) {
    return <PleaseCardListSkeleton />;
  }

  return pleases && pleases.length > 0 ? (
    <PleaseCardList pleases={pleases} />
  ) : (
    <MissingFallback text="아직 해주세요가 없습니다" />
  );
}
