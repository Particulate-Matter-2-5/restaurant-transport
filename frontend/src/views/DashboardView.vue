<script setup>
import Sidebar from '@/components/Sidebar.vue'
import IncomeChart from '@/components/charts/IncomeChart.vue'
import ExpenseChart from '@/components/charts/ExpenseChart.vue'
import TotalChart from '@/components/charts/TotalChart.vue'
</script>

<template>
    <div class="flex">
        <aside class="fixed h-full">
            <Sidebar />
        </aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 min-h-screen h-full"
        >
            <!-- Name section  -->
            <section class="w-full h-12">
                <span class="font-bold text-5xl">Dashboard</span>
            </section>
            <div class="flex flex-row flex-wrap gap-4">
                <div
                    class="m-auto p-6 rounded-lg shadow-md border border-gray-300 bg-[#FDFDFD] flex-1 h-[400px]"
                >
                    <IncomeChart />
                </div>
                <div
                    class="m-auto p-6 rounded-lg shadow-md border border-gray-300 bg-[#FDFDFD] flex-1 h-[400px]"
                >
                    <ExpenseChart />
                </div>
                <div
                    class="m-auto p-6 rounded-lg shadow-md border border-gray-300 bg-[#FDFDFD] flex-1 h-[400px]"
                >
                    <TotalChart />
                </div>
            </div>
        </main>
    </div>
</template>
